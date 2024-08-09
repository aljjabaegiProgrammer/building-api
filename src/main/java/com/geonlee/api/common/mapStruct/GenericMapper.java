package com.geonlee.api.common.mapStruct;

import java.util.List;

/**
 * @author GEONLEE
 * @since 2024-08-09
 */
public interface GenericMapper<R, E> {
    R toRecord(E entity);
    List<R> toRecordList(List<E> entityList);
}
