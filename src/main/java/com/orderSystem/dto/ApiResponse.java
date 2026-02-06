package com.orderSystem.dto;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class ApiResponse<T> {

    private boolean success;

    private String message;

    private T data;

    private LocalDateTime timestamp;
    

    public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public <T> ApiResponse() {
		// TODO Auto-generated constructor stub
	}

	public ApiResponse(boolean success, String message, T data, LocalDateTime timestamp) {
		super();
		this.success = success;
		this.message = message;
		this.data = data;
		this.timestamp = timestamp;
	}

	// -----------------------------
    // SUCCESS RESPONSE
    // -----------------------------
    public static <T> ApiResponse<T> success(String msg, T data) {

        return new ApiResponse<T>(  
                true,
                msg,
                data,
                LocalDateTime.now()
        );
    }

    // -----------------------------
    // ERROR RESPONSE
    // -----------------------------
    public static <T> ApiResponse<T> error(String msg) {

        return new ApiResponse<T>( 
                false,
                msg,
                null,
                LocalDateTime.now()
        );
    }
}
