package mtcom.dongbumms.msgruntimeagent.util;

import java.io.ByteArrayInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;

import mtcom.dongbumms.entity.agree.AgreeRelayFailLogBdt;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class SoapRequestUtil {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private  final int CONNECTION_TIMEOUT = 5000; // 5초
    private  final int READ_TIMEOUT = 5000; // 5초

	
/*	private static String relaySoapURITest = "http://10.10.88.81/junggye/servlet/RelayCallTM";
	private static String relaySoapURISemi = "http://10.10.88.201/junggye/servlet/RelayCallTM";
	private static String relaySoapURIProd = "http://210.124.234.52:9201/junggye/servlet/RelayCallTM";*/
	
	public static String getSoapText(
			String SENDER_CD, String SEND_NO, String SND_TIME, String ERR_CD,
			String SMS_CNT, String USER_CNT, String INFO_CNT, String AGREE_CNT,String END_CNT,
			String SMS_AMT, String USER_AMT, String INFO_AMT, String AGREE_AMT,String END_AMT, 
			String AUTH_NM, String AUTH_HP, String MOBILE_CNT, String MOBILE_AMT,
			//차세대 추가
			String BONBU_CD, String JIJUM_CD, String JUMPO_CD, String REQ_TYPE , String SME_KEY 
			)
	{
		StringBuffer sb = new StringBuffer();
		
		String strErrTime = "";
		String strEndTime = "";
				
		if( ERR_CD.length() > 0 )
		{
			strErrTime = getCurrentTime();
			strErrTime = strErrTime.replaceAll("-", "").replaceAll(",","").replaceAll("\\.","");
		}
		
		if( SND_TIME.length() == 0 )
		{
			SND_TIME = getCurrentTime();
			SND_TIME = SND_TIME.replaceAll("-", "").replaceAll(",","").replaceAll("\\.","");
		}
		
		if( END_CNT.equals("1"))
		{
			strEndTime = getCurrentTime();
			strEndTime = strEndTime.replaceAll("-", "").replaceAll(",","").replaceAll("\\.","");
		}
		
	
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">");  
		sb.append("  <soapenv:Header>");  
		sb.append("  </soapenv:Header>");  
		sb.append("  <soapenv:Body>");
		sb.append("    <request>");
		sb.append("      <channelHeader>");
		sb.append("       <z_rqst_dvcd>S</z_rqst_dvcd>");
		sb.append("       <z_trns_org_dvcd>X</z_trns_org_dvcd>");
		sb.append("       <z_trns_org_cd>MMS</z_trns_org_cd>");
		sb.append("       <z_rc_org_dvcd>K</z_rc_org_dvcd>");
		sb.append("       <z_rc_org_cd>CUS</z_rc_org_cd>");
		sb.append("       <z_tlg_sp_cd>0200</z_tlg_sp_cd>");
		sb.append("       <z_dgs_dvcd>KCUS0147</z_dgs_dvcd>");
		sb.append("       <z_resp_cd/>");
		sb.append("       <z_resp_msg/>");
		sb.append("       <z_trsc_id></z_trsc_id>");
		sb.append("       <z_clt_srv_nm></z_clt_srv_nm>");
		sb.append("       <z_clt_ip></z_clt_ip>");
		sb.append("      </channelHeader>");
		sb.append("      <bizHeader>");
		sb.append("        <z_user_id>");sb.append(SENDER_CD); sb.append("</z_user_id>");
		sb.append("        <z_mac_adr></z_mac_adr>");
		sb.append("        <z_afcp_dvcd></z_afcp_dvcd>");
		sb.append("        <z_orn_dvcd></z_orn_dvcd>");
		sb.append("        <z_orn_no></z_orn_no>");
		sb.append("        <z_stcn_no></z_stcn_no>");
		sb.append("        <z_hdqt_no>");sb.append(BONBU_CD); sb.append("</z_hdqt_no>");
		sb.append("        <z_bzlv_no></z_bzlv_no>");
		sb.append("        <z_bh_no>");sb.append(JIJUM_CD); sb.append("</z_bh_no>");
		sb.append("        <z_brc_no>");sb.append(JUMPO_CD); sb.append("</z_brc_no>");
		sb.append("        <z_cnof_yn></z_cnof_yn>");
		sb.append("        <z_ormm_no></z_ormm_no>");
		sb.append("        <z_ormm_dvcd></z_ormm_dvcd>");
		sb.append("        <z_ormm_tpcd></z_ormm_tpcd>");
		sb.append("        <z_ormm_ptl_tpcd></z_ormm_ptl_tpcd>");
		sb.append("        <z_auth_grp_cd></z_auth_grp_cd>");
		sb.append("        <z_comm_auth_grp_cd></z_comm_auth_grp_cd>");
		sb.append("        <errorCode></errorCode>");
		sb.append("        <z_msg_cd></z_msg_cd>");
		sb.append("        <returnMessage></returnMessage>");
		sb.append("        <z_err_cpn_id></z_err_cpn_id>");
		sb.append("        <z_page_if_key></z_page_if_key>");
		sb.append("        <z_next_page_exis_yn></z_next_page_exis_yn>");
		sb.append("        <z_row_cnt_per_page></z_row_cnt_per_page>");
		sb.append("        <z_user_page_key_set></z_user_page_key_set>");
		sb.append("        <z_rqst_page_no/>");
		sb.append("        <z_wh_cnum/>");
		sb.append("        <z_wh_page_cnt/>");
		sb.append("      </bizHeader>");
		sb.append("      <bizBody>");
		sb.append("        <proc_dvn>3</proc_dvn>");
		sb.append("        <hdlr_empno>"); sb.append(SENDER_CD); sb.append("</hdlr_empno>");
		sb.append("        <csn_rrno>"); sb.append(SEND_NO); sb.append("</csn_rrno>");
		sb.append("        <sms_snd_id>"); sb.append(SME_KEY); sb.append("</sms_snd_id>");
		
		sb.append("        <rc_sms_scrn_ctct_cnum>"); sb.append(SMS_CNT); sb.append("</rc_sms_scrn_ctct_cnum>");
		sb.append("        <rc_slf_ctf_ctct_cnum>"); sb.append(USER_CNT); sb.append("</rc_slf_ctf_ctct_cnum>");
		sb.append("        <rc_csn_scrn_ctct_cnum>"); sb.append(INFO_CNT); sb.append("</rc_csn_scrn_ctct_cnum>");
		sb.append("        <rc_ctf_scrn_ctct_cnum>"); sb.append(AGREE_CNT); sb.append("</rc_ctf_scrn_ctct_cnum>");
		sb.append("        <rc_fin_scrn_ctct_cnum>"); sb.append(END_CNT); sb.append("</rc_fin_scrn_ctct_cnum>");
		
		sb.append("        <rc_sms_scrn_ctct_amt>"); sb.append(SMS_AMT); sb.append("</rc_sms_scrn_ctct_amt>");
		sb.append("        <rc_slf_ctf_ctct_amt>"); sb.append(USER_AMT); sb.append("</rc_slf_ctf_ctct_amt>");
		sb.append("        <rc_csn_scrn_ctct_amt>"); sb.append(INFO_AMT); sb.append("</rc_csn_scrn_ctct_amt>");
		sb.append("        <rc_ctf_scrn_ctct_amt>"); sb.append(AGREE_AMT); sb.append("</rc_ctf_scrn_ctct_amt>");
		sb.append("        <rc_fin_scrn_ctct_amt>"); sb.append(END_AMT); sb.append("</rc_fin_scrn_ctct_amt>");
		
		sb.append("        <sms_err_cd>"); sb.append(ERR_CD); sb.append("</sms_err_cd>");
		sb.append("        <sms_err_ocr_dtl_dttm>"); sb.append(strErrTime); sb.append("</sms_err_ocr_dtl_dttm>");
				
		sb.append("        <rc_fin_scrn_ctct_dtl_dttm>"); sb.append(strEndTime); sb.append("</rc_fin_scrn_ctct_dtl_dttm>");
		sb.append("        <sms_err_cd>"); sb.append(ERR_CD); sb.append("</sms_err_cd>");
		sb.append("        <sms_err_ocr_dtl_dttm>"); sb.append(strErrTime); sb.append("</sms_err_ocr_dtl_dttm>");
		//sb.append("        <csn_ctf_no>"); sb.append(AUTH_NM); sb.append("</csn_ctf_no>");
		sb.append("        <slf_ctf_prop_no>"); sb.append(AUTH_NM); sb.append("</slf_ctf_prop_no>");
		sb.append("        <cust_clpno>"); sb.append(AUTH_HP); sb.append("</cust_clpno>");
		sb.append("        <clp_dvn_ctct_cnum>"); sb.append(MOBILE_CNT); sb.append("</clp_dvn_ctct_cnum>");
		sb.append("        <clp_dvn_ctct_amt>"); sb.append(MOBILE_AMT); sb.append("</clp_dvn_ctct_amt>");	
		sb.append("      </bizBody>");
		sb.append("    </request>");
		sb.append("  </soapenv:Body>");
		sb.append("</soapenv:Envelope>");

		
		String soapText = sb.toString();
		
		return soapText;
	}
	
	public static String getCurrentTime() {
		Calendar cal = Calendar.getInstance( );
	    String s = String.format("%04d-%02d-%02d-%02d.%02d.%02d.%06d",
			cal.get(Calendar.YEAR),
			(cal.get(Calendar.MONTH) + 1),
			cal.get(Calendar.DAY_OF_MONTH),
			cal.get(Calendar.HOUR_OF_DAY),
			cal.get(Calendar.MINUTE),
			cal.get(Calendar.SECOND),
			0
			);
	    return s;
	 }
	
	
	public boolean doSoapRequest(String pSoapText, String soapRequestURL)
	{
		String soapText = pSoapText;
		String soapURI = "";
		
		soapURI = soapRequestURL;
		
		logger.debug("doSoapRequest: request to " + soapURI);
		
		try {
			// Create SoapMessage   
            MessageFactory msgFactory     = MessageFactory.newInstance();   
            SOAPMessage message           = msgFactory.createMessage();   
            SOAPPart soapPart             = message.getSOAPPart();   
    
            // Load the SOAP text into a stream source   
            byte[] buffer                 = soapText.getBytes("utf-8");   
            ByteArrayInputStream stream   = new ByteArrayInputStream(buffer);   
            StreamSource source           = new StreamSource(stream);
    
            // Set contents of message
            //세미계오류
            soapPart.addMimeHeader("Content-Type","text/xml; charset=\"utf-8\"");
            
            // Set contents of message
            soapPart.setContent(source);
            
            System.out.println("\n soapURI==============>\n"+soapURI+"\n");
            System.out.println("\n soapText==============>\n"+soapText+"\n");
            // -- DONE
            //logger.debug("\n\n\nSEND =========================");
			//message.writeTo(System.out);
			//logger.debug("==============================\n\n\n");
            // -- SEND
            SOAPConnection con = SOAPConnectionFactory.newInstance().createConnection();
            URL urlEndpoint = new URL(soapURI);
           
            // SOAP 메시지 준비 및 전송
            //-----------------------
           // jdk1.6 에서는 지원 안되지만, 테스트 함 해봄
            	URLConnection urlConnection = urlEndpoint.openConnection(); // URL로부터 URLConnection 생성
            	urlConnection.setConnectTimeout(15000); // URLConnection에 직접 타임아웃 설정
            	urlConnection.setReadTimeout(15000);    // URLConnection에 직접 타임아웃 설정
           
             	long step1 = System.currentTimeMillis();
              	// SOAP 메시지 준비 및 전송
             	SOAPMessage reply = con.call(message, urlEndpoint);            			
			  	long step2 = System.currentTimeMillis();
			  	reply.writeTo( System.out );
			
			//------------------------------------------------------
            
            //2025
            //jdk1.6은 soap 에 timeout 설정이 없어서 ,  thread 로 요청 하여, thread 시간 설정 
            
            try 
            {
                // 타임아웃이 있는 SOAP 요청 전송
                SOAPMessage response = sendWithTimeout(con, message, urlEndpoint);
                
                // 응답 처리 
                System.out.println("SOAP 응답 성공");
                response.writeTo(System.out);
                
            } catch (SOAPTimeoutException e) {
                System.err.println("SOAP 연결 타임아웃 발생: " + e.getMessage());
                // 타임아웃 처리 로직
                return false;
            } catch (SOAPException e) {
                System.err.println("SOAP 오류: " + e.getMessage());
                return false;
            } finally 
            {
                // 항상 연결 닫기
                if (con != null) 
                {
                    try 
                    {
                    	con.close();
                    } catch (SOAPException e) 
                    {
                        System.err.println("연결 종료 오류: " + e.getMessage());
                    }
                }
            }
          
			//reply.getSOAPBody();
			//logger.debug(reply.getSOAPBody());
			//logger.debug("==============================\n\n\n");
			
			//Check the output
	        //Create the transformer
	        //TransformerFactory transformerFactory = 
	        //                   TransformerFactory.newInstance();
	        //Transformer transformer = 
	        //                transformerFactory.newTransformer();
	        //Extract the content of the reply
	        //Source sourceContent = reply.getSOAPPart().getContent();
	        //Set the output for the transformation
	        //StreamResult result = new StreamResult(System.out);
	        //transformer.transform(sourceContent, result);
	        //System.out.println();
	        
	        //logger.debug("\n\n\nRECVED " + (step2-step1) + " =========================");
	        //logger.debug("soap send success!");
	        // con.close();
		} catch (UnsupportedOperationException ue){
			ue.printStackTrace();
		} catch (SOAPException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();   
			return false;
		}		
		
		return true;
	}
	
	public static AgreeRelayFailLogBdt SaveSoapRequest(
			String PA_CD, String GK_NO, String SND_TIME, String ERR_CD,
			String SMS_CNT, String USER_CNT, String INFO_CNT, String AGREE_CNT,String END_CNT,
			String SMS_AMT, String USER_AMT, String INFO_AMT, String AGREE_AMT,String END_AMT, String AUTH_NM, String AUTH_HP, String SYSTEM_TYPE, String MOBILE_CNT, String MOBILE_AMT)
	{		
		String ERR_TIME = "";
		String END_TIME = "";
		
		if( ERR_CD.length() > 0 )
		{
			ERR_TIME = getCurrentTime();
		}
		
		if( SND_TIME.length() == 0 )
		{
			SND_TIME = getCurrentTime();
		}
		
		if( END_CNT.equals("1"))
		{
			END_TIME = getCurrentTime();
		}
		
		AgreeRelayFailLogBdt log = new AgreeRelayFailLogBdt();
		log.setISUD(3);
		log.setPA_CD(PA_CD);
		log.setGK_NO(GK_NO);
		log.setSND_TIME(SND_TIME);
		log.setERR_CD(ERR_CD);
		log.setERR_TIME(ERR_TIME);
		log.setEND_TIME(END_TIME);
		log.setSMS_CNT(SMS_CNT);
		log.setUSER_CNT(USER_CNT);
		log.setINFO_CNT(INFO_CNT);
		log.setAGREE_CNT(AGREE_CNT);
		log.setEND_CNT(END_CNT);
		log.setSMS_AMT(SMS_AMT);
		log.setUSER_AMT(USER_AMT);
		log.setINFO_AMT(INFO_AMT);
		log.setAGREE_AMT(AGREE_AMT);
		log.setEND_AMT(END_AMT);
		log.setAUTH_NM(AUTH_NM);
		log.setAUTH_HP(AUTH_HP);
		log.setSYSTEM_TYPE(SYSTEM_TYPE!=null?SYSTEM_TYPE:"T");
		log.setMOBILE_CNT(MOBILE_CNT);
		log.setMOBILE_AMT(MOBILE_AMT);
		
		return log;
	}
	
	/**
     * 타임아웃 감지를 위해 별도 스레드에서 SOAP 호출 실행
    */
	private  SOAPMessage sendWithTimeout(final SOAPConnection conn, 
            final SOAPMessage message, 
            final URL endpoint) 
            throws SOAPTimeoutException, SOAPException {

			final SOAPMessageHolder responseHolder = new SOAPMessageHolder();
			final ExceptionHolder exceptionHolder = new ExceptionHolder();
			
			// 별도 스레드에서 SOAP 호출 실행
			Thread soapThread = new Thread(new Runnable() {
				public void run() 
				{
					try 
					{
						SOAPMessage response = conn.call(message, endpoint);
						responseHolder.setMessage(response);
					} catch (Exception e) {
						exceptionHolder.setException(e);
					}
				}
			});
		
		
			// 스레드 시작
			soapThread.start();
		
			try 
			{
				// 타임아웃(밀리초) 동안 스레드 완료 대기
				soapThread.join(CONNECTION_TIMEOUT + READ_TIMEOUT);
				
				// 스레드가 여전히 실행 중인지 확인
				if (soapThread.isAlive()) 
				{
					// 타임아웃 발생, 스레드 중단 시도
					soapThread.interrupt();
					throw new SOAPTimeoutException("SOAP 호출이 " + ((CONNECTION_TIMEOUT + READ_TIMEOUT) / 1000) + "초 내에 완료되지 않았습니다.");
				}
				
				// 예외가 발생했는지 확인
				if (exceptionHolder.getException() != null) 
				{
					if (exceptionHolder.getException() instanceof SOAPException) 
					{
						throw (SOAPException) exceptionHolder.getException();
					} 
					else 
					{
						throw new SOAPTimeoutException("SOAP 호출 중 오류 발생", exceptionHolder.getException());
					}
				}
				
				// 응답 반환
				return responseHolder.getMessage();
			
			} catch (InterruptedException e) 
			{
					throw new SOAPTimeoutException("SOAP 호출 대기 중 인터럽트 발생", e);
			}
		}
				
	/**
     * 타임아웃 처리를 위한 사용자 정의 예외
    */
    private  class SOAPTimeoutException extends Exception {
        public SOAPTimeoutException(String message) {
            super(message);
        }
        
        public SOAPTimeoutException(String message, Throwable cause) {
            super(message, cause);
        }
    }
    
    
    /**
     * SOAP 응답 메시지를 보관하는 홀더 클래스
     */
    private  class SOAPMessageHolder {
        private SOAPMessage message;
        
        public void setMessage(SOAPMessage message) {
            this.message = message;
        }
        
        public SOAPMessage getMessage() {
            return message;
        }
    }
    
    /**
     * 예외를 보관하는 홀더 클래스
     */
    private  class ExceptionHolder {
        private Exception exception;
        
        public void setException(Exception exception) {
            this.exception = exception;
        }
        
        public Exception getException() {
            return exception;
        }
    }
    

}
