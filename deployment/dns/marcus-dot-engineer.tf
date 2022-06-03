resource "cloudflare_zone" "marcus-dot-engineer" {
    zone = "marcus.engineer"
}

module "marcus-dot-engineer-redirect" {
    source = "../dns-redirect"
    cloudflare_zone_id = cloudflare_zone.marcus-dot-engineer.id
    cloudflare_hostname = cloudflare_zone.marcus-dot-engineer.zone
}

resource "cloudflare_record" "home" {
    zone_id     = cloudflare_zone.marcus-dot-engineer.id
    name        = "home"
    value       = "88.98.227.3"
    type        = "A"
    proxied     = false
    ttl         = 1
}
