package codesecurity.api.editors;

import java.beans.PropertyEditorSupport;

public abstract class CaseInsensitiveCodeSecurityTypeEditor<T> extends PropertyEditorSupport {
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        setValue(getText(text));
    }

    protected abstract T getText(String text);
}