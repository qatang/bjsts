package com.bjsts.manager.core.service;

import com.bjsts.core.api.request.ApiRequest;
import com.bjsts.core.api.request.ApiRequestFilter;
import com.bjsts.core.api.request.ApiRequestOrder;
import com.bjsts.core.api.request.ApiRequestPage;
import com.bjsts.core.api.response.ApiResponse;
import com.bjsts.core.enums.EnableDisableStatus;
import com.bjsts.core.enums.OperatorType;
import com.bjsts.core.enums.PageOrderType;
import com.bjsts.manager.core.constants.GlobalConstants;
import com.bjsts.manager.core.query.ISearchable;
import com.bjsts.manager.core.repository.IRepository;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jinsheng
 * @since 2016-04-28 14:15
 */
@Transactional
public abstract class AbstractService<T, ID extends Serializable> implements IService<T, ID> {
    protected final transient Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected IRepository<T, ID> repository;

    @Override
    public T save(T t) {
        return repository.save(t);
    }

    @Override
    public T update(T t) {
        return repository.save(t);
    }

    @Override
    public T get(ID id) {
        return repository.getOne(id);
    }

    @Override
    public void delete(ID id) {
        repository.delete(id);
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public List<T> findAll(Iterable<ID> iterable) {
        return repository.findAll(iterable);
    }

    @Override
    public Page<T> find(ISearchable searchable) {
        if (searchable == null) {
            return repository.findAll(new PageRequest(0, GlobalConstants.DEFAULT_PAGE_SIZE));
        }
        return repository.findAll(searchable.getSpecification(), searchable.getPageable());
    }

    @Override
    public long count(ISearchable searchable) {
        if (searchable == null) {
            return repository.count();
        }
        return repository.count(searchable.getSpecification());
    }

    @Override
    public void updateValid(ID id, EnableDisableStatus toStatus) {

    }

    protected <T> Specification<T> convertSpecification(ApiRequest request) {
        if (request == null) {
            return null;
        }
        return (root, query, cb) -> {
            if (request.getFilterList() != null && !request.getFilterList().isEmpty()) {
                List<Predicate> predicateList = new ArrayList<>();
                for (ApiRequestFilter filter : request.getFilterList()) {
                    Predicate predicate = convertPredicate(filter, root, cb);
                    if (predicate != null) {
                        predicateList.add(predicate);
                    }
                }
                return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
            }
            return null;
        };
    }

    private <T> Predicate convertPredicate(ApiRequestFilter filter, Root<T> root, CriteriaBuilder cb) {
        switch (filter.getOperatorType()) {
            case EQ:
                return cb.equal(root.get(filter.getField()), filter.getValue());
            case GE:
                if (filter.getValue() instanceof Comparable) {
                    return cb.greaterThanOrEqualTo(root.get(filter.getField()), (Comparable) filter.getValue());
                } else {
                    logger.error("字段({})不是可比较对象, value={}", filter.getField(), filter.getValue());
                    return null;
                }
            case LE:
                if (filter.getValue() instanceof Comparable) {
                    return cb.lessThanOrEqualTo(root.get(filter.getField()), (Comparable) filter.getValue());
                } else {
                    logger.error("字段({})不是可比较对象, value={}", filter.getField(), filter.getValue());
                    return null;
                }
            case GT:
                if (filter.getValue() instanceof Comparable) {
                    return cb.greaterThan(root.get(filter.getField()), (Comparable) filter.getValue());
                } else {
                    logger.error("字段({})不是可比较对象, value={}", filter.getField(), filter.getValue());
                    return null;
                }
            case LT:
                if (filter.getValue() instanceof Comparable) {
                    return cb.lessThan(root.get(filter.getField()), (Comparable) filter.getValue());
                } else {
                    logger.error("字段({})不是可比较对象, value={}", filter.getField(), filter.getValue());
                    return null;
                }
            case BETWEEN:
                Object val1 = filter.getValueList().get(0);
                Object val2 = filter.getValueList().get(1);
                if (val1 instanceof Comparable && val2 instanceof Comparable) {
                    return cb.between(root.get(filter.getField()), (Comparable) val1, (Comparable) val2);
                } else {
                    logger.error("字段({})不是可比较对象, value1={}, value2={}", filter.getField(), val1, val2);
                    return null;
                }
            case IN:
                return root.get(filter.getField()).in(filter.getValueList());
            case LIKE:
                return cb.like(root.get(filter.getField()), "%" + filter.getValue() + "%");
            case NOT_NULL:
                return cb.isNotNull(root.get(filter.getField()));
            case NOT_EQUAL:
                return cb.notEqual(root.get(filter.getField()), filter.getValue());
            case AND:
            case OR:
                if (filter.getValueList() == null || filter.getValueList().isEmpty()) {
                    return null;
                }
                if (StringUtils.isNotBlank(filter.getField())) {
                    logger.error("OR 和 AND 操作不允许指定 field: {}", filter.getField());
                    return null;
                }
                List<Predicate> predicateList = Lists.newArrayList();
                for (Object o : filter.getValueList()) {
                    ApiRequestFilter f = (ApiRequestFilter) o;
                    // 递归调用
                    Predicate predicate = convertPredicate(f, root, cb);
                    if (predicate != null) {
                        predicateList.add(predicate);
                    }
                }
                if (!predicateList.isEmpty()) {
                    if (filter.getOperatorType() == OperatorType.AND) {
                        return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
                    }
                    if (filter.getOperatorType() == OperatorType.OR) {
                        return cb.or(predicateList.toArray(new Predicate[predicateList.size()]));
                    }
                }
                return null;
            default:
                logger.error("不支持的运算符, op={}", filter.getOperatorType());
                return null;
        }
    }

    protected Pageable convertPageable(ApiRequestPage requestPage) {
        return new PageRequest(requestPage.getPage(), requestPage.getPageSize(), this.convertSort(requestPage));
    }

    protected Sort convertSort(ApiRequestPage requestPage) {
        if (requestPage.getOrderList() != null && !requestPage.getOrderList().isEmpty()) {
            List<Sort.Order> orderList = new ArrayList<>();
            for (ApiRequestOrder requestOrder : requestPage.getOrderList()) {
                orderList.add(this.convertSortOrder(requestOrder));
            }
            return new Sort(orderList);
        }
        return null;
    }

    private Sort.Order convertSortOrder(ApiRequestOrder requestOrder) {
        Sort.Direction direction;
        if (requestOrder.getOrderType().equals(PageOrderType.DESC)) {
            direction = Sort.Direction.DESC;
        } else {
            direction = Sort.Direction.ASC;
        }
        return new Sort.Order(direction, requestOrder.getField());
    }

    protected <T, E> ApiResponse<E> convertApiResponse(Page<T> page) {
        ApiResponse<E> apiResponse = new ApiResponse<>();
        apiResponse.setPage(page.getNumber());
        apiResponse.setPageSize(page.getSize());
        apiResponse.setTotal(page.getTotalElements());
        apiResponse.setPagedData((List<E>)page.getContent());

        return apiResponse;
    }

}
