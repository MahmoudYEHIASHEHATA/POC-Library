package com.cassbana.antifraud

import androidx.annotation.Keep

object RSConstants {
    const val USER = "user"

    const val FCM_TOKEN = "fcm_token"
    const val DEFAULT_LANGUAGE = "ar"
    const val HAS_SEE_NOTIFICATIONS = "has_see_notifications"
    const val HAS_GRANTED_OTHER_PERMISSIONS = "has_grant_permission"
    const val HAS_GRANTED_LOCATION_PERMISSION = "has_grant_location_permission"
    const val INSURANCE_DIALOG_SHOWN = "is_insurance_dialog_shown"
    const val LATITUDE = "latitude"
    const val LONGTUIDE = "longtuide"
    const val IN_VALID_SELFIE = "IN_VALID_SELFIE"
    const val APP_LANGUAGE = "APP_LANGUAGE"
    const val LANGUAGE_EN = "en"

    /**
     * Notifications types
     * */
    const val NOTIFICATION_IS_ENABLED = "notification_is_enabled"
    const val NEW_NOTIFICATION = "new_notification"
    const val IS_APP_ACTIVE = "is_app_active"
    const val LOAN_CREATION_NOTIFICATION = "loan_creation"
    const val LOAN_REJECTION_NOTIFICATION = "loan_rejection"
    const val FRAUD_LOAN_REJECTION_NOTIFICATION = "fraud_loan_rejection"
    const val LOAN_CONFIRMATION_CODE_NOTIFICATION = "loan_confirmation_code"
    const val LOAN_ACCEPTANCE_NOTIFICATION = "loan_acceptance"
    const val LOAN_COMPLETED_NOTIFICATION = "loan_completed"
    const val COLLECTION_CREATION_NOTIFICATION = "loan_settlement_creation"
    const val COLLECTION_ACCEPTANCE_NOTIFICATION = "loan_settlement_acceptance"
    const val COLLECTION_CONFIRMATION_CODE_NOTIFICATION = "loan_settlement_confirmation_code"
    const val COLLECTION_REJECTION_NOTIFICATION = "loan_settlement_rejection"
    const val COLLECTION_COMPLETED_NOTIFICATION = "loan_settlement_completed"
    const val BECOME_MERCHANT_ACCEPT_NOTIFICATION = "become_merchant_acceptance"
    const val BECOME_MERCHANT_REJECT_NOTIFICATION = "become_merchant_rejection"
    const val EXTRA_LOAN_ACCEPT_NOTIFICATION = "extra_loan_acceptance"
    const val EXTRA_LOAN_REJECT_NOTIFICATION = "extra_loan_rejection"
    const val BLOCKED_NOTIFICATION = "client_blocked"
    const val UNBLOCK_NOTIFICATION = "client_unblocked"
    const val GENERAL_NOTIFICATION = "general"
    const val NOTIFICATION_LIMIT_APPROVED = "approval_accepted"
    const val NOTIFICATION_LIMIT_REJECTED = "approval_rejected"
    const val CLIENT_REWARD_SUCCESS = "client_reward_success"
    const val CLIENT_SCORE_INCREASE = "client_score_increase"
    const val CLIENT_SCORE_DECREASE = "client_score_decrease"
    const val NOTIFICATION_UNFULFILLED_LOANS_RELEASED = "unfulfilled_loans_released"
    const val INSURANCE_REQUEST_REJECTED = "insurance_request_rejected"
    const val FCM_TOKEN_UPDATE = "fcm_token_update"
    const val REJECTED_By = "rejected_by"
    const val REJECTED_By_CUSTOMER = "customer"
    const val REJECTED_By_MERCHANT = "merchant"
    const val REJECTED_By_AGENT = "agent"
    const val IS_ONLINE_INTEGRATION = "is_online_loan"
    const val IS_PAID_ON_CASH = "is_paid_on_cash"
    const val FRAUD_LOAN_REJECTION_CODE = "fraud_code"
    const val NOTIFICATION_COMING_DUES = "up_comming_loan_dues"
    const val NOTIFICATION_PAST_DUES = "past_loan_dues"
    const val DECREASE_WALLET_LIMIT = "decrease_wallet_limit"
    const val INCREASE_WALLET_LIMIT = "increase_wallet_limit"
    const val SHOULD_OPEN_PAYMENT_OPTIONS = "SHOULD_OPEN_PAYMENT_OPTIONS"
    const val FAWRY_PAYMENT_OPTION = "fawry_payment_option"
    const val LAST_SEND_LOCATION = "last_saved_location"
    const val SMS_OTP_VERIFICATION = "sms_otp_verification"


    const val MERCHANT = "merchant"
    const val AGENT = "agent"

    const val LOAN_CANCELLED = "loan_cancelled"

    /**
     * Loan Types
     * */
    const val CLOSED_LOANS = 1
    const val OPEN_LOANS = 2
    const val UNFULFILLED_LOANS = 3

    /**
     * Extra loan images types
     * */
    const val FRONT_ID = 1
    const val BACK_ID = 2
    const val FIRST_COMMERCIAL = 3
    const val SECOND_COMMERCIAL = 4
    const val FIRST_OTHER = 5
    const val SECOND_OTHER = 6

    /**
     * Extra loan images keys
     * */
    const val FRONT_ID_KEY = "national_id_front"
    const val BACK_ID_KEY = "national_id_back"
    const val FIRST_COMMERCIAL_KEY = "commercial_register_1"
    const val SECOND_COMMERCIAL_KEY = "commercial_register_2"
    const val FIRST_OTHER_KEY = "other_1"
    const val SECOND_OTHER_KEY = "other_2"

    /**
     * Extras
     * */
    const val EXTRA_SELECTION_TITLE = "extra_selection_title"
    const val EXTRA_SELECTION_LIST = "extra_selection_list"
    const val EXTRA_USER_TYPE = "extra_user_type"
    const val EXTRA_MOBILE = "extra_mobile"
    const val EXTRA_PASSWORD = "extra_password"
    const val EXTRA_COUNTRY = "extra_country"
    const val EXTRA_IS_FORGET_PASSWORD = "extra_is_forget"
    const val EXTRA_IS_EDIT_PROFILE = "extra_is_edit_profile"
    const val EXTRA_IS_CASH_COLLECTION = "extra_is_cash_collection"
    const val EXTRA_LOAN = "extra_loan"
    const val EXTRA_LOAN_TYPE = "extra_loan_type"
    const val EXTRA_IS_ONLINE_INTEGRATION = "extra_is_online_integration"
    const val EXTRA_IS_PAID_ON_CASH = "extra_is_paid_on_cash"
    const val EXTRA_IS_OPEN_LOAN = "extra_is_open_loan"
    const val EXTRA_LOAN_ID = "extra_loan_id"
    const val EXTRA_OPEN_LENDING = "extra_open_lending"
    const val EXTRA_OPEN_STATUS = "extra_open_status"
    const val EXTRA_OPEN_LENDING_CONFIRMATION = "extra_open_lending_confirmation"
    const val EXTRA_OPEN_LOAN_AMOUNT = "extra_open_loan_amount"
    const val EXTRA_OPEN_TRANSACTION_CONFIRMATION_CODE = "extra_open_transaction_verification_code"
    const val EXTRA_HAS_IMAGE = "extra_has_image"
    const val EXTRA_APP_DATA = "extra_app_data"
    const val EXTRA_IS_CLIENT = "extra_is_client"
    const val EXTRA_CAROUSEL_POSITION = "extra_carousel_position"
    const val EXTRA_CLIENT_LIMIT = "extra_client_limit"
    const val EXTRA_LIMIT_APPROVED = "extra_limit_approved"
    const val EXTRA_LIMIT_REJECTED = "extra_limit_rejected"
    const val EXTRA_AUTH_FLOW = "extra_auth_flow"
    const val EXTRA_IS_CHANGING_PASSWORD = "extra_is_changing_password"
    const val EXTRA_VERIFICATION_CODE = "extra_verification_code"
    const val EXTRA_NEW_LIMIT = "extra_new_limit"
    const val EXTRA_REWARD_SUCCESS = "extra_reward_success"
    const val EXTRA_LIMIT_INCREASED = "increase_wallet_limit"
    const val EXTRA_LIMIT_DECREASED = "decrease_wallet_limit"
    const val EXTRA_CREATED_AT = "extra_created_at"
    const val EXTRA_SELFIE = "Selfie_bundle"
    const val EXTRA_SELFIE_FLOW = "selfie_flow"
    const val EXTRA_INSURANCE = "insurance"
    const val EXTRA_ID_DATA = "id_data"
    const val EXTRA_EDITED_ID_DATA = "edited_id_data"
    /**
     * Flag that shows if the user by passed ocr and fill it manually
     */
    const val EXTRA_BY_PASS_OCR = "by_pass_ocr"

    /**
     * APIs Constants
     * **/
    const val CONTENT_TYPE = "application/json"
    const val PAGINATION_LIMIT = 10
    const val LARGE_PAGE_LIMIT = 100

    /**
     * Remote Config
     */
    const val REMOTE_CONFIG_LATEST_VERSION_CODE = "latest_version_code"
    const val REMOTE_CONFIG_VALIFY_LIVENESS_AND_FACE_MATCH_MODE =
        "valify_Liveness_and_face_match_mode"
    const val REMOTE_CONFIG_CASSBANA_FACE_RECOGNITION_MODE = "cassbana_face_recognition_mode"
    const val REMOTE_CONFIG_CASSBANA_MISSED_CALL_VERIFICATION = "missed_call_status"


    /**
     * Enums
     */
    @Keep
    enum class AuthFlow {
        REGISTER_NEW,
        RESET_PASSWORD_THEN_LOGIN,
        RESET_PASSWORD_THEN_HOME,
        CHANGE_PHONE,
        VERIFY_NEW_DEVICE
    }

    enum class SelfieFlow {
        /**
         * Flow of opening insurance confirmation to finalize redeeming the insurance
         */
        INSURANCE_FLOW,

        /**
         * Flow of opening complete profile to finalize registration
         */
        REGISTRATION_FLOW,
        /**
         * Flow of opening EKYC steps to complete agent flow
         */
        AGENT_COMPLETE_EKYC_FLOW,
        /**
         * Flow of taking a selfie after accepting a loan
         */
        LOAN_FLOW
    }


    /**
     * Valify error codes
     */

    const val NO_INTERNET_CONNECTION_ERROR_CODE = 7001
    const val TRIALS_EXCEEDED_ERROR_CODE = 7005
    const val FACIAL_RECOGNITION_ERROR_CODE = 7201
    const val DOCUMENT_VALIDATION_FAILED_ERROR_CODE = 7101
    const val FRONT_AND_BACK_ID_NUMBER_DIFFERENT_ERROR_CODE = 7103
    const val NEW_DEVICE_ERROR_CODE = 400


    /**
     * Valify configuration modes
     * default = VALIFY_LIVENESS_AND_FACE_MATCH_DISABLED
     */
    const val VALIFY_LIVENESS_AND_FACE_MATCH_ENABLED = 2
    const val VALIFY_ONLY_LIVENESS_ENABLED = 1
    const val VALIFY_LIVENESS_AND_FACE_MATCH_DISABLED = 0
    const val VALIFY_DISABLED = 3

    /**
     * Cassbana face recognition configuration modes
     * default = Constants.CASSBANA_FACE_RECOGNITION_ENABLED
     */
    const val CASSBANA_FACE_RECOGNITION_DISABLED = 0
    const val CASSBANA_FACE_RECOGNITION_ENABLED = 1

    /**
     * ID vcalidation length
     */
    const val ID_VALIDATION_LENGTH = 6

    /**
     * Deeplink
     */
    const val INSURANCE_POLICY = "insurancepolicy"

    /**
     * SinchAppKey
     */
     const val SINCH_APP_KEY =  "2e78d143-e036-436a-9abf-88bdb25a49a7"

    const val THIRD_PARTY_LOAN_COLLECTION = "thirdpartyloancollection"
    const val MERCHANT_ID = "merchant_id"
    const val LOAN_ID = "loan_id"
    const val DEEP_LINK = "deep_link"

    /**
     * EKYC
     */
    const val MALE = "ذكر"
    const val FEMALE = "أنثي"

    /**
     * Privacy and Policy
     */
    const val PRIVACY_POLICY_URL = "https://www.cassbana.com/language/ar/privacy-policy-ar/"
}
