<template>
    <div class="payment-methods">
      <h3>결제 방식</h3>
      <div class="payment-options">
        <button class="payment-button">
          <img src="@/assets/card-payment.png" alt="카드 결제" />
        </button>
        <button class="payment-button">
          <img src="@/assets/bank-transfer.png" alt="무통장 입금" />
        </button>
      </div>
      <div class="payment-options">
        <button class="payment-button">
          <img src="@/assets/kakaopay.png" alt="카카오페이" />
        </button>
        <button class="payment-button">
          <img src="@/assets/naverpay.png" alt="네이버페이" />
        </button>
      </div>
      <div class="payment-options">
        <button class="payment-button">
          <img src="@/assets/tosspay.png" alt="토스페이" />
        </button>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    name: "PaymentMethods",
  };
  </script>
  
  <style scoped>
  .payment-methods {
    background: #ffffff;
    padding: 20px;
    border-radius: 8px;
    box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
    text-align: center;
    width: 100%;
    max-width: 500px;
    margin: 0 auto;
  }
  
  h3 {
    font-size: 18px;
    margin-bottom: 16px;
  }
  
  .payment-options {
    display: flex;
    justify-content: center;
    gap: 10px;
    margin-bottom: 10px;
  }
  
  .payment-button {
    background: none;
    border: none;
    cursor: pointer;
    padding: 5px;
  }
  
  .payment-button img {
    width: 150px; /* 이미지 크기 조정 */
    height: auto;
  }
  </style>
  