if file.open("init.lua") == nil then
        print("init.lua")
else
        print("Running")
        file.close("init.lua")        
end

pin=8

wifi.setmode(wifi.SOFTAP) 
wifi.ap.config({ssid="SSID",pwd="PASSWORD"})

gpio.mode(pin,gpio.OUTPUT)
gpio.write(pin,gpio.LOW)

srv=net.createServer(net.TCP) 
srv:listen(80,function(conn)     
    conn:on("receive", function(client,request)
        local buf = "";
                        
        gpio.write(pin,gpio.HIGH)                       
        gpio.write(pin,gpio.LOW)
                       
        print("{status: "..adc.read(0).."}");
        buf = buf.."{ \"status\": \""..adc.read(0).." \"}";        
        client:send(buf);        
        collectgarbage();
    end)
end)
