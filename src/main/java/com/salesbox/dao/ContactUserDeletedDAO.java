package com.salesbox.dao;

import com.salesbox.entity.ContactUserDeleted;
import com.salesbox.entity.User;
import com.salesbox.entity.enums.ObjectType;

import java.util.List;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: tienhd
 * Date: 6/5/14
 * Time: 4:16 PM
 */
public interface ContactUserDeletedDAO extends BaseDAO<ContactUserDeleted>
{

    public List<ContactUserDeleted> findByUserAndType(User user, ObjectType objectType);

    public List<ContactUserDeleted>  findByObjectIdAndUserAndType(UUID uuid, User user, ObjectType objectType);
}
