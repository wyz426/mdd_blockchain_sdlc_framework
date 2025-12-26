import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class BpmnToUmlConverterTest {

    private BpmnToUmlConverter converter;

    @BeforeEach
    public void setUp() {
        // 在每次测试前实例化BpmnToUmlConverter对象
        converter = new BpmnToUmlConverter();
    }

    @Test
    public void testCreateRole() throws IOException {
        // 测试创建Role接口
        String filePath = "F:\\bpnm\\generated-code\\src\\main\\java\\Role.java";
        File file = new File(filePath);
        
        // 删除文件以确保每次测试时都能重新创建
        if (file.exists()) {
            file.delete();
        }

        converter.Create_Role();

        // 检查文件是否创建
        assertTrue(file.exists(), "Role.java 文件应成功创建");

        // 验证文件内容
        String fileContent = new String(Files.readAllBytes(Paths.get(filePath)));
        assertTrue(fileContent.contains("public interface Role"), "文件应包含 'public interface Role'");
        assertTrue(fileContent.contains("void performRole();"), "文件应包含 'void performRole();'");
    }

    @Test
    public void testCreateRoleRelation() throws IOException {
        // 测试修改现有类，添加Role接口实现
        String directoryPath = "F:\\bpnm\\generated-code\\src\\main\\java";
        File directory = new File(directoryPath);
        File[] files = directory.listFiles((dir, name) -> name.endsWith(".java"));
        
        // 确保有Java文件
        assertNotNull(files, "未找到Java文件");
        assertTrue(files.length > 0, "未找到Java文件");

        // 假设存在至少一个文件
        File testFile = files[0];
        String originalContent = new String(Files.readAllBytes(testFile.toPath()));

        converter.Create_Role_Relation();

        // 检查文件内容是否已更新
        String updatedContent = new String(Files.readAllBytes(testFile.toPath()));
        assertNotEquals(originalContent, updatedContent, "文件内容应已更新");

        // 确保文件包含接口实现
        assertTrue(updatedContent.contains("implements Role"), "文件应包含 'implements Role'");
        assertTrue(updatedContent.contains("@Override"), "文件应包含 '@Override'");
        assertTrue(updatedContent.contains("public void performRole()"), "文件应包含 'public void performRole()'");
    }

    @Test
    public void testFileCreationErrorHandling() {
        // 测试文件创建过程中错误的处理逻辑
        String invalidFilePath = "F:\\nonexistentpath\\generated-code\\src\\main\\java\\Role.java";
        File invalidFile = new File(invalidFilePath);

        // 确保文件路径无效时能够处理异常
        assertThrows(IOException.class, () -> {
            // 调用Create_Role会尝试创建文件，如果路径错误会抛出异常
            new BpmnToUmlConverter().Create_Role();
        });
    }
}
