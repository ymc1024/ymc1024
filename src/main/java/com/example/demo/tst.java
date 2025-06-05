public class CourseFacade {
    private final CourseService courseService;
    
    public void createCourse() {
        String name = "하루 30분 집사 완전정복";
        String description = "인간의 행동과 심리를 배울 수 있는 강의입니다";
        String difficulty = "초급";
        Integer vetId = 1;

        // 인자값을 잘못 넘김
        courseService.createCourse(name, description, description, vetId);
	//리턴값 오류 및 인자 오류    
	String str1 = doSoapRequest(name);    
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
                System.out.println("SOAP 응답 성공:");
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
	
	
	
}


