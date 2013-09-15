package lab1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author antoine
 */
@WebServlet(name = "ClientValidationServlet", urlPatterns = {"/validate"})
public class ClientValidationServlet extends HttpServlet {

    private final static String NAME_REGEX = "^\\pL+$";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE, "GET not supported on this endpoint");
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<String> errorList = new ArrayList<String>();

        Map<String, String[]> param = request.getParameterMap();

        validateName(param.get("firstname"), errorList, "firstname");
        validateName(param.get("surname"), errorList, "surname");
        validateAge(param.get("age"), errorList);
        validateGender(param.get("gender"), errorList);
        
        request.setAttribute("errors", errorList);
        request.getRequestDispatcher("/WEB-INF/validate.jsp").forward(request, response);
    }

    private void validateName(String[] nameArr, List<String> errorList, String nameType) {
        if (nameArr == null || nameArr.length == 0) {
            errorList.add("No " + nameType + " specified");
            return;
        }
        
        String name = nameArr[0];
        
        if (name.isEmpty()) {
            errorList.add("Field \"" + nameType + "\" cannot be empty!");
            return;
        }

        if (!name.matches(NAME_REGEX)) {
            errorList.add('\"' + name + "\" is not a valid " + nameType + ", must contain only letters.");
            return;
        }

        System.out.println(nameType + " is fine");

    }

    private void validateAge(String[] ageArr, List<String> errorList) {
        if (ageArr == null || ageArr.length == 0) {
            errorList.add("No age specified.");
            return;
        }

        String ageStr = ageArr[0];
        
        if (ageStr.isEmpty()) {
            errorList.add("Field \"Age\" cannot be empty.");
            return;
        }
        
        Integer age;
        try {
            age = Integer.parseInt(ageStr);
        } catch (NumberFormatException e) {
            errorList.add("Age must be a valid integer: was \"" + ageStr +"\"");
            return;
        }

        if (age < 0) {
            errorList.add("Age is less than 0: was " + age);
            return;
        } else if (age > 120) {
            errorList.add("Age is greater than 120: was " + age);
            return;
        }

        System.out.println("Age is valid");
    }

    private void validateGender(String[] genderArr, List<String> errorList) {
        if (genderArr == null || genderArr.length == 0) {
            errorList.add("No gender specified.");
            return;
        }
        
        String gender = genderArr[0];
        
        if (gender.isEmpty()) {
            errorList.add("Field \"Gender\" cannot be empty and must be one of 'M', 'm', 'F' or 'f'.");
            return;
        }
        
        switch (gender.toLowerCase().charAt(0)) {
            case 'm':
                // fallthrough
            case 'f':
                System.out.println("Gender is valid.");
                return;
            default:
                errorList.add("Gender must be one of 'M', 'm', 'F' or 'f', was \"" + gender + '\"');
        }
    }
}
