package org.jboss.seam.ui;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;

import org.jboss.seam.text.SeamTextLexer;
import org.jboss.seam.text.SeamTextParser;

import antlr.ANTLRException;
public class UIFormattedText extends UIOutput             
{
   public static final String COMPONENT_TYPE = "org.jboss.seam.ui.FormattedText";      
   public static final String COMPONENT_FAMILY = "org.jboss.seam.ui.FormattedText";
   
   @Override
   public String getFamily()
   {
      return COMPONENT_FAMILY;
   }
   
   @Override
   public void encodeBegin(FacesContext context) throws IOException
   {
      if ( !isRendered() || getValue() == null) return;

      Reader r = new StringReader( (String) getValue() );
      SeamTextLexer lexer = new SeamTextLexer(r);
      SeamTextParser parser = new SeamTextParser(lexer);
      try
      {
         parser.startRule();
      }
      catch (ANTLRException re)
      {
         throw new RuntimeException(re);
      }
      context.getResponseWriter().write(parser.toString());
   }
   

    @Override
    public void encodeEnd(FacesContext context) 
        throws IOException
    {
    }
    
    @Override
   public String getRendererType()
   {
      return null;
   }
}
