package application;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;

@XmlRootElement(name="errands")
public class ErrandsListeWrapperr 
{
	private List<Errands> errands;
	@XmlElement(name="errands")
	public List<Errands> getErrands()
	{
		return errands;
	}
	public void setErrands(List<Errands> errands)
{
	this.errands=errands;
}

}
