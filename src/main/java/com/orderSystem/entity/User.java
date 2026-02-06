package com.orderSystem.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(
    name = "users",
    indexes = {
        @Index(name = "idx_user_email", columnList = "email", unique = true)
    }
)

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @Email(message = "Invalid email")
    @NotBlank(message = "Email required")
    private String email;
    @NotBlank(message = "Name is required")
    private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User(Long id, @Email(message = "Invalid email") @NotBlank(message = "Email required") String email,
			@NotBlank(message = "Name is required") String name) {
		super();
		this.id = id;
		this.email = email;
		this.name = name;
	}

	public User() {
		super();
	}
    
}
