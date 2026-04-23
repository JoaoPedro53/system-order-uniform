package external.depedency;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
public class Connection {
    private String url;
    private String username;
    private String password;
}
