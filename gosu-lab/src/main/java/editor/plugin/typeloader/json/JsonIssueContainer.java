package editor.plugin.typeloader.json;

import manifold.internal.javac.IIssue;
import java.util.Collections;
import java.util.List;
import javax.script.ScriptException;
import manifold.internal.javac.IIssueContainer;

/**
 */
public class JsonIssueContainer implements IIssueContainer
{
  private final JsonIssue _issue;

  public JsonIssueContainer()
  {
    _issue = null;
  }

  public JsonIssueContainer( ScriptException cause )
  {
    _issue = new JsonIssue( cause );
  }

  @Override
  public List<IIssue> getIssues()
  {
    return _issue == null ? Collections.emptyList() : Collections.singletonList( _issue );
  }

  @Override
  public List<IIssue> getWarnings()
  {
    return Collections.emptyList();
  }

  @Override
  public List<IIssue> getErrors()
  {
    return getIssues();
  }

  @Override
  public boolean isEmpty()
  {
    return _issue == null;
  }
}
