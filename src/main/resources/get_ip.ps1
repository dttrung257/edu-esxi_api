$Name = $args[0]
Connect-VIServer "192.168.0.112" -User root -Password Trung@65730208 -SaveCredentials | Out-Null
$found = 0

while ($found -ne 1) {
	Start-Sleep -Seconds 5
	$ip = (Get-VM -Name $Name).Guest.IPAddress[0]
	if ("$ip" -match '(\b25[0-5]|\b2[0-4][0-9]|\b[01]?[0-9][0-9]?)(\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)){3}') {
		$found = 1
		Write-Host $ip
	}
}