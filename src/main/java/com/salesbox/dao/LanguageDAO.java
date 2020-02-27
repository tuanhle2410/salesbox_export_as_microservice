package com.salesbox.dao;

import com.salesbox.entity.Language;

import java.util.List;
import java.util.UUID;

/**
 * Created by hunglv on 7/26/14.
 */
public interface LanguageDAO extends BaseDAO<Language>
{
    Language findOne(UUID uuid);

    List<Language> findAll();

    Language findByName(String name);

    Language findByDescription(String description);
}
