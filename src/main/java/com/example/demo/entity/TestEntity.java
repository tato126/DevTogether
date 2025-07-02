package com.example.demo.entity;

import jakarta.persistence.*;

@Table(name = "Test_Write_Entity")
@Entity
public class TestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 생성자
    public TestEntity(String title, String content) {
        // ID는 자동생성된다.
        this.title = title;
        this.content = content;
    }

    private String title;
    private String content;

    // for hibernate
    protected TestEntity() {
    }

    // Getter
    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
