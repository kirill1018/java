package feature;

public class Checklist {
	@TestId(value = "tc-103", order = 3)
	void checkCommentAdded() {}
	@TestId(value = "tc-101", order = 1)
	void checkLogin() {}
	@TestId(value = "tc-104", order = 4)
	void checkLog() {}
	@TestId(value = "tc-102", order = 2)
	void checkTaskCreat() {}
	void checkMark() {}
}