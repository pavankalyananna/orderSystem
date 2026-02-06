package com.orderSystem.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
public class OrderRequest {

	private Long userId;

	private List<Item> items;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	@Getter
	@Setter
	public static class Item {
		private Long productId;
		private Integer quantity;
		public Long getProductId() {
			return productId;
		}
		public void setProductId(Long productId) {
			this.productId = productId;
		}
		public Integer getQuantity() {
			return quantity;
		}
		public void setQuantity(Integer quantity) {
			this.quantity = quantity;
		}
		
	}
}
