import os
import sys
import smtplib
from email.MIMEMultipart import MIMEMultipart
from email.mime.application import MIMEApplication
from email.MIMEText import MIMEText
from mailconfig import username, password

fromaddr = username
toaddrs  = sys.argv[1]

msg = MIMEMultipart()
msg.attach(MIMEText(sys.argv[3]))
attachment = MIMEApplication(file(sys.argv[4]).read())
attachment.add_header('Content-Disposition', 'attachment', filename=os.path.basename(sys.argv[4]))
msg.attach(attachment)
msg['Subject'] = sys.argv[2]
msg['To'] = toaddrs

server = smtplib.SMTP('smtp.gmail.com:587')
server.ehlo()
server.starttls()
server.ehlo()
server.login(username,password)  
server.sendmail(fromaddr, toaddrs, msg.as_string())
server.quit()

