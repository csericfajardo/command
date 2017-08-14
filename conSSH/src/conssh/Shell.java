/* -*-mode:java; c-basic-offset:2; indent-tabs-mode:nil -*- */
/**
 * This program enables you to connect to sshd server and get the shell prompt.
 *   $ CLASSPATH=.:../build javac Shell.java 
 *   $ CLASSPATH=.:../build java Shell
 * You will be asked username, hostname and passwd. 
 * If everything works fine, you will get the shell prompt. Output may
 * be ugly because of lacks of terminal-emulation, but you can issue commands.
 *
 */
package conssh;
import com.jcraft.jsch.*;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import javax.swing.*;
import javax.swing.text.JTextComponent;

public class Shell{
  public static void main(String[] arg){
    
    try{
      JSch jsch=new JSch();

      
     
      
     
      Session session=jsch.getSession("leccgn","advim7.itg.ti.com", 22);

      String passwd = JOptionPane.showInputDialog("Enter password");
      session.setPassword(passwd);

      UserInfo ui = new MyUserInfo(){
        public void showMessage(String message){
          JOptionPane.showMessageDialog(null, message);
        }
        public boolean promptYesNo(String message){
          Object[] options={ "yes", "no" };
          int foo=JOptionPane.showOptionDialog(null, 
                                               message,
                                               "Warning", 
                                               JOptionPane.DEFAULT_OPTION, 
                                               JOptionPane.WARNING_MESSAGE,
                                               null, options, options[0]);
          return foo==0;
        }

        // If password is not given before the invocation of Session#connect(),
        // implement also following methods,
        //   * UserInfo#getPassword(),
        //   * UserInfo#promptPassword(String message) and
        //   * UIKeyboardInteractive#promptKeyboardInteractive()

      };

      session.setUserInfo(ui);

      // It must not be recommended, but if you want to skip host-key check,
      // invoke following,
      // session.setConfig("StrictHostKeyChecking", "no");

      //session.connect();
      session.connect(30000);   // making a connection with timeout.

      Channel channel=session.openChannel("shell");

      // Enable agent-forwarding.
      //((ChannelShell)channel).setAgentForwarding(true);
      
      channel.setInputStream(System.in);
      
      /*
      // a hack for MS-DOS prompt on Windows.
      channel.setInputStream(new FilterInputStream(System.in){
          public int read(byte[] b, int off, int len)throws IOException{
            return in.read(b, off, (len>1024?1024:len));
          }
        });
       */
     
//      JTextComponent textComponent = new JTextPane();
//        JScrollPane scrollPane = new JScrollPane( textComponent );
//
//        JFrame.setDefaultLookAndFeelDecorated(true);
//        JFrame frame = new JFrame("Message Console");
//        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
//        frame.getContentPane().add( scrollPane );
//        frame.setSize(400, 120);
//        frame.setVisible(true);
//
//        MessageConsole console = new MessageConsole(textComponent);
//        console.redirectOut();
//        console.redirectErr(Color.RED, null);

      
      channel.setOutputStream(System.out);
      OutputStream s=channel.getOutputStream();
      
      
      
      
      //ByteArrayOutputStream bos = (ByteArrayOutputStream)s;
      //byte b [] = bos.toByteArray();
      //for(int x = 0; x < bos.size(); x++) {
         // printing the characters
      //   System.out.print((char)b[x]  + "   "); 
      //}
      /*
      // Choose the pty-type "vt102".
      ((ChannelShell)channel).setPtyType("vt102");
      */

      /*
      // Set environment variable "LANG" as "ja_JP.eucJP".
      ((ChannelShell)channel).setEnv("LANG", "ja_JP.eucJP");
      */

      //channel.connect();
      channel.connect(3*1000);
    }
    catch(Exception e){
      System.out.println(e);
    }
  }

  public static abstract class MyUserInfo
                          implements UserInfo, UIKeyboardInteractive{
    public String getPassword(){ return null; }
    public boolean promptYesNo(String str){ return false; }
    public String getPassphrase(){ return null; }
    public boolean promptPassphrase(String message){ return false; }
    public boolean promptPassword(String message){ return false; }
    public void showMessage(String message){ }
    public String[] promptKeyboardInteractive(String destination,
                                              String name,
                                              String instruction,
                                              String[] prompt,
                                              boolean[] echo){
      return null;
    }
  }
}