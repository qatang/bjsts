package com.bjsts.manager.entity.idgenerator;

import com.bjsts.manager.core.entity.AbstractEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 发号器
 * 
 * @author wangzhiliang
 */
@Entity
@Table(name = "id_generator")
@DynamicInsert
@DynamicUpdate
public class IdGeneratorEntity extends AbstractEntity {

	private static final long serialVersionUID = 4793001757755915291L;
	@Id
	private String sequence;	//序列器的名字

	@Column(name = "current_value", nullable = false)
	private Long currentValue;	//当前值

	@Column(name = "group_key")
	private String groupKey;	//指定分组时按分组递增, 一旦分组发生变化立即复位计数器
	
	public String getSequence() {
		return sequence;
	}
	public void setSequence(String sequence) {
		this.sequence = sequence;
	}
	public Long getCurrentValue() {
		return currentValue;
	}
	public void setCurrentValue(Long currentValue) {
		this.currentValue = currentValue;
	}

	public String getGroupKey() {
		return groupKey;
	}

	public void setGroupKey(String groupKey) {
		this.groupKey = groupKey;
	}
}
