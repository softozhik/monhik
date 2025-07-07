package me.yozh.monhik.repository;

import me.yozh.monhik.entity.Routes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoutesRepository extends JpaRepository<Routes, UUID>, JpaSpecificationExecutor<Routes> {
}