package com.tvapp.repository;

import com.tvapp.model.ApiModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApiRepository extends JpaRepository<ApiModel, Integer> {

    ApiModel findByName(String name);
}