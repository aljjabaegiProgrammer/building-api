package com.geonlee.api.domin.authority;

import com.geonlee.api.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author GEONLEE
 * @since 2024-08-19
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
