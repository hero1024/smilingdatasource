package cn.edu.moe.smiling.datasource.model;

import javax.validation.groups.Default;

/**
 * @author songpeijiang
 * @since 2024/4/10
 */
public interface ValidGroup extends Default {

    interface Crud extends ValidGroup{
        interface Create extends Crud{

        }

        interface Update extends Crud{

        }

        interface Query extends Crud{

        }

        interface Delete extends Crud{

        }
    }
}
