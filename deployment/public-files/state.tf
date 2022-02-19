terraform {
    backend "s3" {
        bucket = "tfstate-marcuscraske-dot-com"
        key    = "public-files.terraform.tfstate"
        dynamodb_table = "tfstate-marcuscraske-dot-com"
        region = "eu-west-1"
    }
}
