package com.kh.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PizzaServlet
 */
@WebServlet("/confirmPizza.do")
public class PizzaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PizzaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// System.out.println("잘 요청됐나??");
		// 1) 전달값 중에 한글이 있을 경우 인코딩 처리(post방식일때만)
		// request.setCharacterEncoding("UTF-8");
		
		// 2) 요청 시 전달값 뽑기 및 데이터 가공처리(파싱 같은 거) => 변수 및 객체 기록
		// request.getParameter("키값") : "벨류값" (String)
		// request.getParameterValues("키값") : ["벨류값", "벨류값",...] (String[])
		// => 만일 키 값이 존재하지 않을 경우 null 반환
		
		// 미션 : 요청 시 전달값들 다 뽑아서 변수에 기록해놓기 (변수명은 jsp의 name값과 똑같이)
		//		  콘솔에 값 출력 => 8개 값! ㄴ pizzaOrderForm.jsp에 있는 name과 동일하게 변수명 작성
		
		String userName = request.getParameter("userName"); // "차은우"
		String phone = request.getParameter("phone"); // "01011112222"
		String address = request.getParameter("address"); // "서울시 강남구"
		String message = request.getParameter("message"); // "빠른 배달 요청" | ""
		
		String pizza = request.getParameter("pizza"); // "콤비네이션피자" | "치즈피자" ...ㅡ 선택된 피자 1개(선택 안할 수 없음)
		String[] toppings = request.getParameterValues("topping"); // ["고구마무스", "치즈바이트"] | null
		String[] sides = request.getParameterValues("side"); // ["핫소스", "피클"] | null
		
		String payment = request.getParameter("payment"); // "card" | "cash" ㅡ 선택된 결제 방식 1개
		
		
		System.out.println("이름 : " + userName);
		System.out.println("전화번호 : " + phone);
		System.out.println("주소 : " + address);
		System.out.println("요청사항 : " + message);
		
		System.out.println("피자 : " + pizza);
			
		if(toppings == null) {
			System.out.println("토핑 : 없음");
		}else {
			System.out.println("토핑 : " + String.join("/", toppings));
		}		
		
		if(sides == null) {
			System.out.println("사이드 : 없음");
		}else {
			System.out.println("사이드 : " + String.join("/", sides));
		}
		
		System.out.println("결제방식 : " + payment);		
		
		// 3) 요청 처리 (db에 sql문 실행하러 > service > dao ㅡ 했다 치고) ㅡ controller라고 생각하면 됨
		
		int price = 0;
		
		switch(pizza) { // ㅡ 피자 1개만 선택,, 배열 아니라 for문 안 돌림
		case "콤비네이션피자" : price += 5000; break;
		case "치즈피자" : price += 6000; break;
		case "포테이토피자" : // price += 7000; break;
		case "고구마피자" : price += 7000; break;
		case "불고기피자" : price += 8000; break;
		}
		
		if(toppings != null) { // 토핑이 null이 아닐때만 돌리기 ㅡ 배열인 경우라서 for문 돌리고
			for(int i=0; i<toppings.length; i++) { // ["콘크림무스", "치즈바이트"]
				switch(toppings[i]) {
				case "고구마무스" : 
				case "콘크림무스" : price += 1500; break;
				case "파인애플토핑" : 
				case "치즈토핑" : price += 2000; break;
				case "치즈바이트" : 
				case "치즈크러스트" : price += 3000; break;
				}
			}
		}
		
		if(sides != null) { // 사이드가 null이 아닐때만 돌리기
			for(int i=0; i<sides.length; i++) {
				switch(sides[i]) {
				case "콜라" :
				case "사이다" : price += 2000; break;
				case "핫소스" :
				case "갈릭소스" : price += 300; break;
				case "피클" : 
				case "파마산치즈가루" : price += 500; break;
				}
			}
		}
		
		// 4) 요청 처리 후 사용자가 보게 될 응답페이지 (결제페이지) 만들기
		// 응답페이지(jsp)를 선택해서 포워딩
		// 단, 응답페이지에서 필요한 데이터 있다면 담아서 포워딩 할 것! ㅡ controller 에서 veiw단으로 가져간다고 생각
		// request > attribute 영역에 담기!!(세팅함)
		
		request.setAttribute("userName", userName);
		request.setAttribute("phone", phone);
		request.setAttribute("address", address);
		request.setAttribute("message", message);
		request.setAttribute("pizza", pizza);
		request.setAttribute("toppings", toppings);
		request.setAttribute("sides", sides);
		request.setAttribute("payment", payment);
		request.setAttribute("price", price);
		
		// 응답할 뷰 jsp 선택!! ㅡ 응답 객체(getRequestDispatcher)를 만들어서 RequestDispatcher 자료형의 view라는 변수에 담음
		RequestDispatcher view = request.getRequestDispatcher("views/pizza/pizzaPayment.jsp"); // (jsp의 경로!!) // pizzaPayment.jsp 복사해서 컨쉬알에 검색했을 때 파일 없으면 404오류
		// view에는 응답하려고 하는 jsp가 들어가있음!!
		// 저 jsp로 날아가라!!
		view.forward(request, response); // response는 응답하려면 필요함★★ Q. jsp가 안띄워진다?? ㅡ jsp 경로 오입력, response 입력 누락
		
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
