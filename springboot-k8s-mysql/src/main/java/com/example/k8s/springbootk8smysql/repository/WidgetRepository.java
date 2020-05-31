package com.example.k8s.springbootk8smysql.repository;
import com.example.k8s.springbootk8smysql.entity.Widget;
import org.springframework.data.jpa.repository.JpaRepository;
public interface WidgetRepository extends JpaRepository<Widget, Long> {

}