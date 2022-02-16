resource "cloudflare_zone" "limpygnome-dot-com" {
    zone = "limpygnome.com"
}

module "limpygnome-dot-com-redirect" {
    source = "../dns-redirect"
    cloudflare_zone_id = cloudflare_zone.limpygnome-dot-com.id
    cloudflare_hostname = cloudflare_zone.limpygnome-dot-com.zone
}
