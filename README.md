# 로그인 & 회원가입 과제테스트
- **Firebase Authentication**을 사용하여 로그인 & 회원가입 기능을 구현했습니다.
- **Repositioy패턴**과 **MVVM패턴**을 적용하여 비지니스로직과 UI를 분리해, 향후 유지보수가 용이하도록 코드를 작성했습니다.
- Activity는 Fragment보다 상대적으로 무겁기떄문에, **Single Activity Architecture(SAA)방식**을 적용하여 메모리효율성을 높였습니다.
<br/><br/><br/>
### ⚙️적용기술
<div align=left>
<img src="https://img.shields.io/badge/Repository-83B81A?style=flat-square&logo=Repository&logoColor=white"/>
<img src="https://img.shields.io/badge/MVVM-83B81A?style=flat-square&logo=Repository&logoColor=white"/>
<img src="https://img.shields.io/badge/Flow-7F52FF?style=flat-square&logo=Repository&logoColor=white"/>
<img src="https://img.shields.io/badge/Coroutine-7F52FF?style=flat-square&logo=Repository&logoColor=white"/>
<img src="https://img.shields.io/badge/Hilt-FF5A5F?style=flat-square&logo=Repository&logoColor=white"/>
<img src="https://img.shields.io/badge/FirebaseAuthentication-FFA500?style=flat-square&logo=Repository&logoColor=white"/>
</div>


<br/><br/><br/>
### 📸 스크린샷


### 회원가입
<img src="https://github.com/user-attachments/assets/1b3009c7-0671-49c2-add0-50fdb6c411bd" width="200" height="400">

- 이메일주소, 비밀번호, 비밀번호 확인, 닉네임 모두작성후 이용약관에 모두 동의해야 회원가입 가능
- 이메일,비밀번호가 정해진 형식에 맞지않을경우 관련 에러메지지 띄움
- 회원가입에 성공하면 Firebase Authentication에 회원가입한 사용자 저장후, 로그인화면으로 이동

  
### 로그인
<img src="https://github.com/user-attachments/assets/c72f7ce4-f4d4-4fd1-a921-3c0828908220" width="200" height="400">

- 이메일,비밀번호가 정해진 형식에 맞지않을경우 관련 에러메지지 띄움
- Firebase Authentication에 저장된 사용자일경우 로그인 성공
  



### 로그아웃 & 회원탈퇴
<p align="left">
  <img src="https://github.com/user-attachments/assets/907b043d-1fdc-4625-b76e-de49cb423c47" width="200" height="400" />
  <img src="https://github.com/user-attachments/assets/1008966d-1fba-42fb-b88e-d87d3edc961a" width="200" height="400" />
</p>

- 로그인 성공시, 닉네임을 가져와 화면에 보여줌
- 로그아웃시, 로그인화면으로 이동
- 회원탈퇴시, Firebase Authentication에 저장된 사용자정보가 삭제되면서 메인화면으로 이동
