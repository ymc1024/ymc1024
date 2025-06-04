public class CourseFacade {
    private final CourseService courseService;
    
    public void createCourse() {
        String name = "하루 30분 집사 완전정복";
        String description = "인간의 행동과 심리를 배울 수 있는 강의입니다";
        String difficulty = "초급";
        Integer vetId = 1;

        // 인자값을 잘못 넘김
        courseService.createCourse(name, description, description, vetId);
    }
}