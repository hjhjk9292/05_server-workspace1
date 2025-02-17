package com.kh.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RequestPostServlet
 */
@WebServlet("/test2.do")
public class RequestPostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RequestPostServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("doGet 메소드 실행");
		
		// 요청 시 전달된 값들은 request의 parameter 영역에 담겨 있음
		
		// POST 방식 요청 같은 경우는
		// 뽑기 "전"!!!!!!**************** 에 인코딩 설정해야됨!! => utf-8
		
		// URL의 Body에 담겨옴! => post 방식의 경우
		// URL의 Body 방식의 인코딩이 UTF-8이 아님! ㅡ get방식과 post방식의 차이는 ** utf-8로 인코딩 **
		
		request.setCharacterEncoding("utf-8");
		
		String name = request.getParameter("name"); // "차은우" | ""
		String gender = request.getParameter("gender"); // "M" | "F" | null
		int age = Integer.parseInt(request.getParameter("age")); // "20" -> 20
		String city = request.getParameter("city"); //"서울"
		double height = Double.parseDouble(request.getParameter("height")); // "160" => 160.0
		
		String[] foods = request.getParameterValues("food"); // ["한식"] | null
		
		System.out.println("name : " + name);
		System.out.println("gender : " + gender);
		System.out.println("age : " + age);
		System.out.println("city : " + city);
		System.out.println("height : " + height);
		
		if(foods == null) {
			System.out.println("foods : 없음");
		}else {
			System.out.println("foods : " + String.join("/", foods)); // 반복문 안 돌리고도 가능한 새로운 방법 ㅡ 돌리지 않고도 배열의 모든 요소를 출력(/ 로 배열 구분 시켜줌)
		}
		
		// 요청처리(db의 sql문 실행) : Service > dao > db
		
		// 요청처리 다 했다는 가정 하에 사용자가 보게 될 응답 html
		
		// 1. 순수 Servlet 방식으로 작성하는 방법 : Java 코드 내에 html을 기술
		// 2. JSP(Java Server Page) 방식 : html 내에 Java 코드를 쓸 수 있음

		// 응답 페이지를 만드는 과정을 jsp에게 위임(떠넘기기)
		
		// 단, 응답화면(jsp)에서 필요로 하는 데이터들을 주섬주섬 담아서 전달해줘야함
		// 주섬주섬 담기 위한 공간 == request의 attribute 영역(키-벨류 세트로 저장)
		// request.setAttribute("키",벨류);
		
		request.setAttribute("name", name); // name이라는 키값에 "차"로 셋팅
		request.setAttribute("age", age);
		request.setAttribute("city", city);
		request.setAttribute("height", height);
		request.setAttribute("gender", gender);
		request.setAttribute("foods", foods); // foods라는 키값으로 배열이 셋팅
		
		// 응답하고자 하는 뷰(jsp) 선택하면서 RequestDispatcher 객체 생성
		// == jsp 띄워라 ㅡ jsp를 띄우기 위해서는 이런 코드를 써야한다~~
		RequestDispatcher view = request.getRequestDispatcher("views/responsePage.jsp"); // RequestDispatcher 자료형의 view 변수의 request.getRequestDispatcher("jsp경로!!");
		view.forward(request, response); // request라는 객체에 셋팅해뒀기 때문에 request도 같이 가져감 // forward 작업을 해줘야지 위의 jsp로 날아감!!! ★★
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doPost 메소드 실행");
		doGet(request, response);
	}

}
