package store.exception.file;

public enum FileErrorMessage {
    FILE_NOT_FOUND("지정된 파일을 찾을 수 없습니다."),
    IO_EXCEPTION("파일 입출력 오류가 발생합니다.");

    private final String message;

    FileErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
