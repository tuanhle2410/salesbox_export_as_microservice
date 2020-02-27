package com.salesbox.entity;

import com.salesbox.entity.task.TaskBase;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * User: luult
 * Date: 5/20/14
 */
@Entity
@OptimisticLocking(type = OptimisticLockType.DIRTY)
@Table(name = "task")
public class Task extends TaskBase {
}
