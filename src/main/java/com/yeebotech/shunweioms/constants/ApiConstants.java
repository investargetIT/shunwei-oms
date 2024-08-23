package com.yeebotech.shunweioms.constants;

public class ApiConstants {

    // Success Messages
    public static final String MESSAGE_SUCCESS_SUPPLIERS_RETRIEVED = "Successfully retrieved all suppliers.";
    public static final String MESSAGE_SUCCESS_SUPPLIER_RETRIEVED = "Successfully retrieved supplier.";
    public static final String MESSAGE_SUCCESS_SUPPLIER_CREATED = "Supplier created successfully.";
    public static final String MESSAGE_SUCCESS_SUPPLIER_UPDATED = "Supplier updated successfully.";
    public static final String MESSAGE_SUCCESS_SUPPLIER_DELETED = "Supplier deleted successfully.";
    public static final String MESSAGE_SUCCESS_SUPPLIERS_DELETED = "Suppliers deleted successfully"; // 新增的成功消息

    // Error Messages
    public static final String MESSAGE_FAILED_TO_RETRIEVE_SUPPLIERS = "Failed to retrieve suppliers.";
    public static final String MESSAGE_FAILED_TO_RETRIEVE_SUPPLIER = "Failed to retrieve the supplier.";
    public static final String MESSAGE_FAILED_TO_CREATE_SUPPLIER = "Failed to create the supplier.";
    public static final String MESSAGE_FAILED_TO_UPDATE_SUPPLIER = "Failed to update the supplier.";
    public static final String MESSAGE_FAILED_TO_DELETE_SUPPLIER = "Failed to delete the supplier.";
    public static final String MESSAGE_NO_SUPPLIER_WITH_ID = "No supplier found with the given ID.";
    public static final String MESSAGE_ERROR_INVALID_REQUEST = "Invalid request.";
    public static final String MESSAGE_ERROR_INTERNAL_SERVER = "Internal server error.";
    public static final String MESSAGE_FAILED_TO_DELETE_SUPPLIERS = "Failed to delete suppliers"; // 新增的失败消息

    // HTTP Status Codes
    public static final int CODE_SUCCESS = 200;
    public static final int CODE_CREATED = 201;
    public static final int CODE_NO_CONTENT = 204;
    public static final int CODE_BAD_REQUEST = 400;
    public static final int CODE_NOT_FOUND = 404;
    public static final int CODE_INTERNAL_SERVER_ERROR = 500;


    // Business Success Codes (if applicable)
    public static final int CODE_BUSINESS_SUCCESS = 10000; // Example custom business error code
    // Business Error Codes (if applicable)
    public static final int CODE_BUSINESS_ERROR = 1000; // Example custom business error code
}
