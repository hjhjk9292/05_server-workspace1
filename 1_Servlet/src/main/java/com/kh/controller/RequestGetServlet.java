package com.kh.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RequestGetServlet
 */
@WebServlet("/test1.do")
public class RequestGetServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RequestGetServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// GET 방식으로 요청 시 해당 이 doGET 메소드가 저절로 호출됨
		// System.out.println("이거 진짜 되나??");
		
		/*
		 * 첫번째 매개변수인 request에는 요청 시 전달된 내용들 담겨 있음 (사용자가 입력한 값, 요청전송방식, 요청자의 ip주소 등등)
		 * 두번째 매개변수인 response에는 요청 처리 후 응답할 때 사용되는 객체
		 * 
		 * 요청 처리를 위해서 요청 시 전달된 값 뽑기
		 * request의 parameter 영역 안에 존재 (키-벨류 세트로 담겨있는 주머니)
		 * 
		 * 따라서 request의 parameter 영역으로부터 전달된 데이터 뽑는 메소드
		 * > request.getParameter("키값") : String(그에 해당하는 value값들) ㅡ 반환됨,, 단건(name=차은우,age=20,,,)에 사용
		 * > request.getParameterValues("키값") : String[](그에 해당하는 value값들) ㅡ 다건(food=한식,food=중식,food=양식)
		 */
		
		String name = request.getParameter("name"); // "차은우" | ""
		String gender = request.getParameter("gender"); // "M" | "F" | null
		int age = Integer.parseInt(request.getParameter("age")); // 20 | "" => 오류가 뜸 (NumberFormatException) : 빈문자열 숫자로 변환 불가 ㅡ 입력안하면 빈문자열로 빈값이 담김..
		String city = request.getParameter("city");
		double height = Double.parseDouble(request.getParameter("height")); // "160" => 160.0 ㅡ 문자열로 받아온 것을 형변환
		
		// 체크박스와 같이 복수개의 벨류값들 뽑고자 할 때
		String[] foods = request.getParameterValues("food"); // ["한식", "중식"] | null
		
		System.out.println("name : " + name);
		System.out.println("gender : " + gender);
		System.out.println("age : " + age);
		System.out.println("city : " + city);
		System.out.println("height : " + height);
		
		// System.out.println("foods : " + foods); // 아마 주소값 나올꺼임
		// System.out.println("foods : " + foods[0]); // 근데 이거는 오류날 수도 있음 null이면! ㅡ 인덱스에 접근 시 null값이면 NullPointeException
		
		if(foods == null) {
			System.out.println("foods : 없음");
		}else {
			System.out.println("foods : " + String.join("/", foods)); // 반복문 안 돌리고도 가능한 새로운 방법 ㅡ 돌리지 않고도 배열의 모든 요소를 출력(/ 로 배열 구분 시켜줌)
		}
		
		// 뽑아낸 값(요청 시 전달된 값)들을 가지고 요청처리 해야됨 (db와 상호작용 : 조회, 삽입, 수정, 삭제...)
		// >Service 메소드 호출 > Dao 메소드 호출 > DB SQL문 실행
		
		/*
		int result = new MemberService().insertMember(name,gender,age,city,height,foods);
		if(result > 0) {
			// 성공 => 성공페이지
		}else {
			// 실패 => 실패페이지
		}
		*/
		
		// 위의 요청 처리 후 성공했다는 가정하에 사용자가 보게 될 응답페이지 만들어서 전송
		// 즉, 여기 "Java 코드 내에" 사용자가 보게 될 응답 HTML 구문 작성할꺼!!!
		
		// 장점 : Java 코드 내에 작성하기 때문에 반복문이나, 조건문, 유용한 메소드 같은걸 활용할 수 있음.
		// 단점 : 불편함, 복잡함, 혹시라도 후에 html 수정해야한다면 Java 코드 수정하는거라
		//		  다시 반영하고자 한다면 서버를 재실행 해야됨
		
		// * response 객체를 통해 사용자에게 html(응답화면) 전달
		
		// 1) 이제부터 내가 출력할 내용은 문서형태의 html이고 문자 인코딩 문자셋은 UTF-8이다라는걸 선언
		response.setContentType("text/html; charset=utf-8");
		
		// 2) 응답하고자 하는 사용자와의 스트림 생성
		PrintWriter out = response.getWriter();
		
		// 3) 저 스트림을 통해 응답 html 구문을 한 줄씩 출력
		out.println("<html>");
		
		out.println("<head>");
		out.println("</head>");
		
		out.println("<body>");
		
		out.println("<h2>개인정보 응답 화면</h2>");
		
		// out.println("<span>" + name + "</span>님은");
		out.printf("<span>%s</span> 님은 ", name);
		out.printf("<span>%d</span> 살이고 ", age);
		out.printf("<span>%s</span>에 사는, ", city);
		out.printf("<span>%.1f</span>cm이고 ", height);
		
		out.print("성별은 ");
		if(gender == null) {
			out.println("선택을 안했습니다. <br>");
		}else {
			if(gender.equals("M")) {
				out.println("<span>남자</span>입니다 <br>");
			}else {
				out.println("<span>여자</span>입니다 <br>");
			}
		}
		
		out.print("좋아하는 음식은 ");
		
		if(foods == null) {
			out.println("없습니다.");
		}else { // ["한식","분식"]
			
			out.println("<ul>");
				for(int i=0; i<foods.length; i++) {
					out.println("<li>" + foods[i] + "</li>");
				}
			out.println("</ul>");
		}
		
		out.println("</body>");
		
		
		out.println("</html>");
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
