package com.angela.codingdojo.repositories;

import com.angela.codingdojo.model.Package;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface PackageRepository extends CrudRepository<Package, Integer> {

    Optional<Package> findByName(String name);

    @Query("SELECT p FROM Package p LEFT JOIN _user u ON u.currentPackage = p GROUP BY p.id ORDER BY COUNT(u.id) DESC")
    Iterable<Package> findAllOrderBySubscribed();
}
