package com.example.todoservice.repositories;

import com.example.todoservice.models.ItemDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DetailsRepository extends JpaRepository<ItemDetails,Integer>
{

}
