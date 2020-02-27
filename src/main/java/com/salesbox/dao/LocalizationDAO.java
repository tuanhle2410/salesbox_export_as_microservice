package com.salesbox.dao;

import com.salesbox.entity.Language;
import com.salesbox.entity.Localization;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * User: luult
 * Date: 8/14/14
 */
public interface LocalizationDAO extends BaseDAO<Localization>
{
    Localization findByLanguageAndKeyCode(Language language, String code);

    List<Localization> findByLanguageAndKeyCodeIn(Language language, List<String> keyCodeList);

    List<Object[]> findLanguageIdAndLocalizationByLanguageIdInAndKeyCode(Collection<UUID> languageIdList, String keyCode);

    List<Localization> findAll();

}
