package com.bjsts.manager.form.system;

import com.bjsts.manager.entity.system.ResourceEntity;
import com.bjsts.manager.core.form.AbstractForm;

/**
 * @author jinsheng
 * @since 2016-05-12 18:29
 */
public class ResourceForm extends AbstractForm {

    private static final long serialVersionUID = 123515295047880821L;

    private ResourceEntity resource;

    public ResourceEntity getResource() {
        return resource;
    }

    public void setResource(ResourceEntity resource) {
        this.resource = resource;
    }
}
