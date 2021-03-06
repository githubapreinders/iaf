/*
   Copyright 2013 Nationale-Nederlanden

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package nl.nn.adapterframework.processors;

import org.apache.commons.lang.StringUtils;

import nl.nn.adapterframework.core.IPipeLineSession;
import nl.nn.adapterframework.core.SenderException;
import nl.nn.adapterframework.core.TimeOutException;
import nl.nn.adapterframework.senders.SenderWrapperBase;
import nl.nn.adapterframework.stream.Message;

/**
 * @author  Gerrit van Brakel
 * @since   4.11
 */
public class InputOutputSenderWrapperProcessor extends SenderWrapperProcessorBase {

	@Override
	public Message sendMessage(SenderWrapperBase senderWrapperBase, Message message, IPipeLineSession session) throws SenderException, TimeOutException {
		Message senderInput=message;
		if (StringUtils.isNotEmpty(senderWrapperBase.getGetInputFromSessionKey())) {
			senderInput=new Message(session.get(senderWrapperBase.getGetInputFromSessionKey()));
			if (log.isDebugEnabled()) log.debug(senderWrapperBase.getLogPrefix()+"set contents of session variable ["+senderWrapperBase.getGetInputFromSessionKey()+"] as input ["+senderInput+"]");
		} else {
			if (StringUtils.isNotEmpty(senderWrapperBase.getGetInputFromFixedValue())) {
				senderInput=new Message(senderWrapperBase.getGetInputFromFixedValue());
				if (log.isDebugEnabled()) log.debug(senderWrapperBase.getLogPrefix()+"set input to fixed value ["+senderInput+"]");
			}
		}
		Message result = senderWrapperProcessor.sendMessage(senderWrapperBase, message, session);
		if (StringUtils.isNotEmpty(senderWrapperBase.getStoreResultInSessionKey())) {
			if (log.isDebugEnabled()) log.debug(senderWrapperBase.getLogPrefix()+"storing results in session variable ["+senderWrapperBase.getStoreResultInSessionKey()+"]");
			session.put(senderWrapperBase.getStoreResultInSessionKey(),result);
		}
		return senderWrapperBase.isPreserveInput()?message:result;
	}

}
