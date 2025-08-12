package org.pahappa.systems.kpiTracker.views.department;

import lombok.Getter;
import lombok.Setter;
import org.pahappa.systems.kpiTracker.core.services.DepartmentService;
import org.pahappa.systems.kpiTracker.models.department.Department;
import org.pahappa.systems.kpiTracker.security.HyperLinks;
import org.sers.webutils.client.views.presenters.ViewPath;
import org.sers.webutils.model.exception.OperationFailedException;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;

@ManagedBean
@Getter
@Setter
@ViewScoped
@ViewPath(path = HyperLinks.DEPARTMENT_VIEW)
public class DepartmentView implements Serializable {
    private static final long serialVersionUID = 1L;
    @ManagedProperty(value = "#{departmentService}")
    private DepartmentService departmentService;
    private List<Department> departments;
    private Department selectedDepartment;

    private String searchTerm;
    private final int maximumresults = 10;
    private final String fileName = "departments";

    @PostConstruct
    public void init() {
        reloadFilterReset();
    }

    public void reloadFilterReset() {
        this.departments = departmentService.getAllInstances();
    }
    public void deleteDepartment(Department department) {
        try {
            departmentService.deleteInstance(department);
            reloadFilterReset(); // Refresh the table

            // Standard JSF success message
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Department deleted successfully."));

        } catch (OperationFailedException e) {
            // Standard JSF error message
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Operation Failed: " + e.getMessage()));
        }
    }

}
