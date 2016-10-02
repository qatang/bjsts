package com.bjsts.core.api.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.concurrent.Callable;
import java.util.function.Consumer;

/**
 * 事务管理操作组件
 * author: sunshow.
 */
public class PlatformTransactionComponent {

    @Autowired
    private PlatformTransactionManager transactionManager;

    public <R> R executeTransaction(Callable<R> callable, Consumer<R> onNext) {
        TransactionDefinition def = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);

        try {
            R result = callable.call();

            transactionManager.commit(status);

            if (onNext != null) {
                onNext.accept(result);
            }

            return result;
        } catch (Exception e) {
            transactionManager.rollback(status);

            if (e instanceof RuntimeException) {
                throw (RuntimeException) e;
            } else {
                throw new RuntimeException(e);
            }
        }

    }

}
