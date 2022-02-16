variable "cloudflare_zone_id" {
    description = "Cloudflare zone ID"
    type = string
}

variable "cloudflare_hostname" {
    description = "Cloudflare zone hostname"
    type = string
}


resource "cloudflare_record" "redirect" {
    zone_id     = var.cloudflare_zone_id
    name        = var.cloudflare_hostname
    value       = "1.2.3.4"
    type        = "A"
    proxied     = true
    ttl         = 1
}

resource "cloudflare_record" "www-redirect" {
    zone_id     = var.cloudflare_zone_id
    name        = "www"
    value       = var.cloudflare_hostname
    type        = "CNAME"
    proxied     = true
}

resource "cloudflare_page_rule" "redirect" {
    zone_id     = var.cloudflare_zone_id
    target      = "*${var.cloudflare_hostname}/*"
    priority    = 1
    status      = "active"

    actions {
        forwarding_url {
            url         = "https://marcuscraske.com/$2"
            status_code = 301
        }
    }
}
