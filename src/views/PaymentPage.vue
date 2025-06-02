<template>
    <div class="payment-page">
      <header class="header">
        <img src="@/assets/logo.png" alt="Chewing Class Logo" class="logo" />
      </header>
  
      <div class="content">
        <section class="box">
          <h2 class="black-title">상품 정보</h2>
          <div class="info">
            <div class="thumb"></div>
            <div class="details">
              <h3>디자이너를 위한 데이터 수집과 시각화</h3>
              <p class="meta">UXUI 디자인 <span class="divider">|</span> 정영호</p>
              <span class="desc">강의 요약</span>
            </div>
          </div>
        </section>
  
        <section class="box">
          <h2 class="black-title">결제 정보</h2>
          <div class="row"><span>주문 금액</span><span>400,000원</span></div>
          <div class="row"><span>프로모션 할인</span><span>- 80,000원</span></div>
          <hr />
          <div class="row total"><span>총 결제 금액</span><span class="total-price">320,000원</span></div>
        </section>
  
        <section class="box">
          <h2 class="black-title">결제 방식</h2>
          <div class="grid">
            <div class="method"><div class="text">카드 결제</div></div>
            <div class="method"><div class="text">무통장 입금</div></div>
            <div class="method"><img src="@/assets/kakao-pay.png" alt="Kakao Pay" /></div>
            <div class="method"><img src="@/assets/naver-pay.png" alt="Naver Pay" /></div>
            <div class="method"><img src="@/assets/toss-pay.png" alt="Toss Pay" /></div>
          </div>
        </section>
  
        <div class="submit">320,000원 결제하기</div>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    name: "PaymentPage"
  };
  </script>
  
  <style scoped>
  .payment-page {
    font-family: Pretendard, sans-serif;
    background: #fff;
    color: #1b1d1f;
  }
  
  .header {
    background: #e3f2ff;
    padding: 20px 120px;
  }
  
  .logo {
    width: 191px;
    height: 28px;
  }
  
  .content {
    max-width: 588px;
    margin: 40px auto;
    display: flex;
    flex-direction: column;
    gap: 40px;
  }
  
  .box {
    background: #f7f8f9;
    padding: 32px;
    border-radius: 8px;
  }
  
  .black-title {
    color: #000;
  }
  
  .thumb {
    width: 80px;
    height: 80px;
    border-radius: 8px;
    background: #e9ebed;
  }
  
  .info {
    display: flex;
    gap: 24px;
  }
  
  .details {
    display: flex;
    flex-direction: column;
    justify-content: center;
    gap: 8px;
  }
  
  h3 {
    font-size: 17px;
    font-weight: 600;
    color: #1b1d1f;
  }
  
  .meta {
    color: #5a5f66;
    font-size: 14px;
  }
  
  .divider {
    margin: 0 8px;
    color: #c9cdd3;
  }
  
  .desc {
    font-size: 14px;
    color: #73787e;
  }
  
  .row {
    display: flex;
    justify-content: space-between;
    margin: 12px 0;
    color: #5a5f66;
    font-size: 15px;
  }
  
  .total-price {
    color: #0f84f4;
    font-weight: 600;
  }
  
  .grid {
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    gap: 16px;
    margin-top: 16px;
  }
  
  .method {
    background: white;
    border: 1px solid #e9ebed;
    border-radius: 4px;
    height: 49px;
    display: flex;
    align-items: center;
    justify-content: center;
    padding: 0 16px;
  }
  
  .method img {
    height: 20px;
    max-width: 100%;
    object-fit: contain;
  }
  
  .text {
    font-size: 14px;
    font-weight: 500;
    color: #1b1d1f;
  }
  
  .submit {
    background: #c9cdd3;
    padding: 16px;
    border-radius: 8px;
    text-align: center;
    color: #73787e;
    font-weight: 500;
  }
  </style>
  