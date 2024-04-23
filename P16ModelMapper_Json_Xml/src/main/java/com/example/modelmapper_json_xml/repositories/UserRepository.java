package com.example.modelmapper_json_xml.repositories;

import com.example.modelmapper_json_xml.entiti.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
}
