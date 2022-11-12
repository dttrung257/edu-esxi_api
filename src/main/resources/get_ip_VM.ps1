$IpServer = $args[0]
$ServerUsername = $args[1]
$Password = $args[2]
$Name = $args[3]
Connect-VIServer "$IpServer" -User $ServerUsername -Password $Password -SaveCredentials | Out-Null
$found = 0

while ($found -ne 1) {
	Start-Sleep -Seconds 5
	$ip = (Get-VM -Name $Name).Guest.IPAddress[0]
	if ("$ip" -match '(\b25[0-5]|\b2[0-4][0-9]|\b[01]?[0-9][0-9]?)(\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)){3}') {
		$found = 1
		Write-Host $ip
	}
}