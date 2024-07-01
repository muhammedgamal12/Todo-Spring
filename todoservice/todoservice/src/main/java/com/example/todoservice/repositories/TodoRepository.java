package com.example.todoservice.repositories;

import com.example.todoservice.models.ItemDetails;
import com.example.todoservice.models.Todo;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<Todo,Integer>
{

    @Transactional
    @Modifying
    @Query("update Todo t set t.title=?1 ,t.itemDetails = ?2 where t.id = ?3")
    Todo updateTodo(String title, ItemDetails details, int id);




}
