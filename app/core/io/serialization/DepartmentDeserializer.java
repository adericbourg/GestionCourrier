package core.io.serialization;

import models.person.Department;

/**
 * @author adericbourg
 */
public class DepartmentDeserializer extends StaticListDeserializer<Department> {
    @Override
    Department valueFor(String value) {
        return Department.valueOf(value);
    }
}
