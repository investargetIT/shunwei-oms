package com.shunwei.oms.common.constants;

public class ApiConstants {

    // Success Messages
    public static final String MESSAGE_SUCCESS_SUPPLIERS_RETRIEVED = "Successfully retrieved all suppliers.";
    public static final String MESSAGE_SUCCESS_SUPPLIER_RETRIEVED = "Successfully retrieved supplier.";
    public static final String MESSAGE_SUCCESS_SUPPLIER_CREATED = "Supplier created successfully.";
    public static final String MESSAGE_SUCCESS_SUPPLIER_UPDATED = "Supplier updated successfully.";
    public static final String MESSAGE_SUCCESS_SUPPLIER_DELETED = "Supplier deleted successfully.";
    public static final String MESSAGE_SUCCESS_SUPPLIERS_DELETED = "Suppliers deleted successfully";

    public static final String MESSAGE_SUCCESS_GOODS_RETRIEVED = "Successfully retrieved all goods.";
    public static final String MESSAGE_SUCCESS_GOODS_CREATED = "Goods created successfully.";
    public static final String MESSAGE_SUCCESS_GOODS_UPDATED = "Goods updated successfully.";
    public static final String MESSAGE_SUCCESS_GOODS_DELETED = "Goods deleted successfully";

    // 新增 GoodsCategory 的成功消息
    public static final String MESSAGE_SUCCESS_CATEGORY_CREATED = "Category created successfully.";
    public static final String MESSAGE_SUCCESS_CATEGORY_UPDATED = "Category updated successfully.";
    public static final String MESSAGE_SUCCESS_CATEGORY_DELETED = "Category deleted successfully.";
    public static final String MESSAGE_SUCCESS_CATEGORY_RETRIEVED = "Successfully retrieved category.";
    public static final String MESSAGE_SUCCESS_CATEGORIES_RETRIEVED = "Successfully retrieved all categories.";

    // 新增 Customer 的成功消息
    public static final String MESSAGE_SUCCESS_CUSTOMER_CREATED = "Customer created successfully.";
    public static final String MESSAGE_SUCCESS_CUSTOMER_UPDATED = "Customer updated successfully.";
    public static final String MESSAGE_SUCCESS_CUSTOMER_DELETED = "Customer deleted successfully.";
    public static final String MESSAGE_SUCCESS_CUSTOMER_RETRIEVED = "Successfully retrieved customer.";
    public static final String MESSAGE_SUCCESS_CUSTOMERS_RETRIEVED = "Successfully retrieved all customers.";


    // Error Messages
    public static final String MESSAGE_FAILED_TO_RETRIEVE_SUPPLIERS = "Failed to retrieve suppliers.";
    public static final String MESSAGE_FAILED_TO_RETRIEVE_SUPPLIER = "Failed to retrieve the supplier.";
    public static final String MESSAGE_FAILED_TO_CREATE_SUPPLIER = "Failed to create the supplier.";
    public static final String MESSAGE_FAILED_TO_UPDATE_SUPPLIER = "Failed to update the supplier.";
    public static final String MESSAGE_FAILED_TO_DELETE_SUPPLIER = "Failed to delete the supplier.";
    public static final String MESSAGE_NO_SUPPLIER_WITH_ID = "No supplier found with the given ID.";
    public static final String MESSAGE_ERROR_INVALID_REQUEST = "Invalid request.";
    public static final String MESSAGE_ERROR_INTERNAL_SERVER = "Internal server error.";
    public static final String MESSAGE_FAILED_TO_DELETE_SUPPLIERS = "Failed to delete suppliers";

    public static final String MESSAGE_FAILED_TO_RETRIEVE_GOODS = "Failed to retrieve goods.";
    public static final String MESSAGE_NO_GOODS_WITH_ID = "No goods found with the given ID.";
    public static final String MESSAGE_FAILED_TO_CREATE_GOODS = "Failed to create the goods.";
    public static final String MESSAGE_FAILED_TO_UPDATE_GOODS = "Failed to update the goods.";
    public static final String MESSAGE_FAILED_TO_DELETE_GOODS = "Failed to delete the goods.";

    // HTTP Status Codes
    public static final int CODE_SUCCESS = 200;
    public static final int CODE_CREATED = 201;
    public static final int CODE_NO_CONTENT = 204;
    public static final int CODE_BAD_REQUEST = 400;
    public static final int CODE_NOT_FOUND = 404;
    public static final int CODE_INTERNAL_SERVER_ERROR = 500;

    // Business Success Codes (if applicable)
    public static final int CODE_BUSINESS_SUCCESS = 10000;

    // Business Error Codes (if applicable)
    public static final int CODE_BUSINESS_ERROR = 1000;
}
