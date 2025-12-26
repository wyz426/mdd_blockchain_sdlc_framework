import com.squareup.javapoet.TypeName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MethodInfo {
    private String className;
    private TypeName returnType;
    private String methodName;
    private List<MethodInfo> nextMethods;
}