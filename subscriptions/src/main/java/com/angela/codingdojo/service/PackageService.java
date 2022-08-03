package com.angela.codingdojo.service;

import com.angela.codingdojo.model.Package;
import com.angela.codingdojo.repositories.PackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PackageService {

    @Autowired
    PackageRepository pRepository;

    public Package save(Package aPackage) {
        return pRepository.save(aPackage);
    }

    public void delete(Package apPackage) {
        pRepository.delete(apPackage);
    }

    public Package SetAvailable(Package aPackage, boolean state) {
        aPackage.setAvailable(state);
        return save(aPackage);
    }

    public Package findById(Integer id) {
        return pRepository.findById(id).orElse(null);
    }

    public Package findByName(String name) {
        return pRepository.findByName(name).orElse(null);
    }

    public Iterable<Package> findAll() {
        return pRepository.findAll();
    }

    public Iterable<Package> findAllOrderBySubscribed(){
        return pRepository.findAllOrderBySubscribed();
    }

}
